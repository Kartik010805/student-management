const BASE_URL = "https://student-management-04rg.onrender.com";

// LOGIN
function login() {
    let u = document.getElementById("username").value;
    let p = document.getElementById("password").value;

    if (!u || !p) {
        alert("Enter username and password");
        return;
    }

    let token = btoa(u + ":" + p);
    localStorage.setItem("auth", token);

    window.location.href = "students.html";
}

// AUTH (SAFE VERSION)
function getAuth() {
    let token = localStorage.getItem("auth");

    if (!token || token === "null" || token === "undefined") {
        alert("Session expired. Please login again.");
        window.location.href = "index.html";
        return null;
    }

    return "Basic " + token;
}

// HANDLE RESPONSE
function handleResponse(res) {
    if (res.status === 401) {
        alert("Unauthorized! Check username/password.");
        localStorage.removeItem("auth");
        window.location.href = "index.html";
        throw new Error("401 Unauthorized");
    }

    if (!res.ok) {
        throw new Error("Request failed: " + res.status);
    }

    return res.json();
}

// COMMON FETCH WRAPPER
function fetchWithAuth(url, options = {}) {
    let auth = getAuth();
    if (!auth) return;

    options.headers = {
        ...(options.headers || {}),
        "Authorization": auth
    };

    return fetch(url, options);
}

// LOAD STUDENTS
function loadStudents() {
    fetchWithAuth(`${BASE_URL}/students`)
        .then(handleResponse)
        .then(data => {
            let tbody = document.querySelector("#studentTable tbody");
            tbody.innerHTML = "";

            data.forEach(s => {
                tbody.innerHTML += `
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.name}</td>
                        <td>${s.email}</td>
                        <td>${s.department}</td>
                        <td>${s.year}</td>
                        <td>
                            <button onclick="deleteStudent(${s.id})">Delete</button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(err => console.error(err));
}

// ADD STUDENT
function addStudent() {
    fetchWithAuth(`${BASE_URL}/students`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            department: document.getElementById("department").value,
            year: parseInt(document.getElementById("year").value)
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to add student");
        loadStudents();
    })
    .catch(err => console.error(err));
}

// DELETE STUDENT
function deleteStudent(id) {
    fetchWithAuth(`${BASE_URL}/students/${id}`, {
        method: "DELETE"
    })
    .then(res => {
        if (!res.ok) throw new Error("Delete failed");
        loadStudents();
    })
    .catch(err => console.error(err));
}

// LOAD ATTENDANCE
function loadAttendance() {
    let id = document.getElementById("studentId").value;

    if (!id) {
        alert("Enter student ID first");
        return;
    }

    fetchWithAuth(`${BASE_URL}/attendance/student/${id}`)
        .then(handleResponse)
        .then(data => {
            let tbody = document.querySelector("#attendanceTable tbody");
            tbody.innerHTML = "";

            data.forEach(a => {
                tbody.innerHTML += `
                    <tr>
                        <td>${a.id}</td>
                        <td>${a.studentId}</td>
                        <td>${a.date}</td>
                        <td>${a.status}</td>
                    </tr>
                `;
            });
        })
        .catch(err => console.error(err));
}

// MARK ATTENDANCE
function markAttendance() {
    fetchWithAuth(`${BASE_URL}/attendance`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            studentId: parseInt(document.getElementById("studentId").value),
            date: document.getElementById("date").value,
            status: document.getElementById("status").value
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Attendance failed");
        loadAttendance();
    })
    .catch(err => console.error(err));
}