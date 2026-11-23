<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 1 & 2: register.jsp
  - Flat number field blur: calls /checkFlat?flatNo=... via XMLHttpRequest (Task 1)
  - Shows green/red message under field without reloading
  - Form submit: fetch() POST with URLSearchParams body, event.preventDefault() (Task 2)
  - Constraints: NO jQuery, NO JSON library. Pure Vanilla JavaScript!
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SocietyPay - Resident Registration (AJAX)</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f1f5f9; max-width: 540px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 32px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.06); }
        h2 { color: #1e3a8a; margin-top: 0; text-align: center; }
        label { display: block; margin-top: 16px; font-weight: bold; color: #334155; }
        input[type="text"], input[type="email"] { width: 100%; padding: 11px; margin-top: 6px; border: 1px solid #cbd5e1; border-radius: 6px; box-sizing: border-box; font-size: 14px; transition: border 0.2s; }
        input:focus { border-color: #2563eb; outline: none; }
        .feedback { font-size: 13px; font-weight: bold; margin-top: 6px; min-height: 18px; }
        .text-available { color: #16a34a; }
        .text-taken { color: #dc2626; }
        .btn-submit { background: #2563eb; color: white; border: none; padding: 13px; border-radius: 6px; cursor: pointer; font-size: 16px; font-weight: bold; width: 100%; margin-top: 24px; transition: background 0.2s; }
        .btn-submit:hover { background: #1d4ed8; }
        .alert-box { padding: 14px; border-radius: 6px; margin-top: 20px; font-size: 14px; display: none; }
        .alert-success { background: #dcfce7; color: #15803d; border: 1px solid #bbf7d0; }
        .alert-error { background: #fee2e2; color: #b91c1c; border: 1px solid #fecaca; }
        .test-guide { margin-top: 25px; background: #f8fafc; border: 1px dashed #94a3b8; border-radius: 6px; padding: 12px; font-size: 12px; color: #475569; }
        .test-guide code { color: #0284c7; font-weight: bold; }
    </style>
</head>
<body>

    <div class="card">
        <h2>📝 Resident Registration</h2>
        <p style="text-align: center; color: #64748b; font-size: 14px; margin-top: -5px;">SocietyPay Asynchronous AJAX Form</p>

        <!-- Alert box for submission results -->
        <div id="statusAlert" class="alert-box"></div>

        <form id="regForm">
            <!-- Flat Number Field with Blur Live Checking -->
            <label for="flatNo">Flat Number:</label>
            <input type="text" id="flatNo" name="flatNo" placeholder="e.g. B-204" required autocomplete="off">
            <div id="flatFeedback" class="feedback"></div>

            <label for="residentName">Resident Full Name:</label>
            <input type="text" id="residentName" name="residentName" placeholder="e.g. Priyanshu Dave" required>

            <label for="email">Resident Email:</label>
            <input type="email" id="email" name="email" placeholder="e.g. priyanshu@example.com" required>

            <button type="submit" class="btn-submit">⚡ Register Resident</button>
        </form>

        <div class="test-guide">
            <strong>Testing Guide (No Page Reload!):</strong><br>
            • Taken Flats (Red error): <code>A-101</code>, <code>B-201</code><br>
            • Free Flats (Green available): <code>B-204</code>, <code>C-303</code><br>
            • Bad Email Test: Enter without <code>@</code> symbol to verify rejection.
        </div>

        <p style="text-align: center; margin-top: 20px; font-size: 14px;">
            <a href="dues.jsp" style="color: #2563eb; text-decoration: none; font-weight: bold;">👉 Go to Task 3: Dues Viewer API</a>
        </p>
    </div>

    <script>
        // ============================================================
        // TASK 1: Live checking with XMLHttpRequest (No jQuery, No reload)
        // ============================================================
        var flatInput = document.getElementById('flatNo');
        var flatFeedback = document.getElementById('flatFeedback');

        flatInput.addEventListener('blur', function () {
            var flat = flatInput.value.trim();
            if (flat === "") {
                flatFeedback.textContent = "";
                return;
            }

            // Constraint: Use pure XMLHttpRequest
            var xhr = new XMLHttpRequest();
            var url = "${pageContext.request.contextPath}/checkFlat?flatNo=" + encodeURIComponent(flat);

            xhr.open("GET", url, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    if (response.available === true) {
                        flatFeedback.textContent = "✔ Flat " + flat + " is available!";
                        flatFeedback.className = "feedback text-available";
                    } else {
                        flatFeedback.textContent = "✖ Flat " + flat + " is already occupied!";
                        flatFeedback.className = "feedback text-taken";
                    }
                }
            };
            xhr.send();
        });

        // ============================================================
        // TASK 2: Asynchronous Submit with fetch() and URLSearchParams
        // ============================================================
        var form = document.getElementById('regForm');
        var statusAlert = document.getElementById('statusAlert');

        form.addEventListener('submit', function (event) {
            // Constraint: call event.preventDefault() to stop page reload
            event.preventDefault();

            var flatNo = document.getElementById('flatNo').value.trim();
            var residentName = document.getElementById('residentName').value.trim();
            var email = document.getElementById('email').value.trim();

            // Constraint: Hint states FormData sends multipart, so use URLSearchParams body
            var params = new URLSearchParams();
            params.append('flatNo', flatNo);
            params.append('residentName', residentName);
            params.append('email', email);

            fetch("${pageContext.request.contextPath}/resident/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: params
            })
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                statusAlert.style.display = "block";
                if (data.ok === true) {
                    statusAlert.className = "alert-box alert-success";
                    statusAlert.innerHTML = "🎉 <strong>Success!</strong> Resident registered for flat " + flatNo + " without page reload.";
                    form.reset();
                    flatFeedback.textContent = "";
                } else {
                    statusAlert.className = "alert-box alert-error";
                    statusAlert.innerHTML = "❌ <strong>Registration Failed:</strong> " + data.message;
                }
            })
            .catch(function (error) {
                statusAlert.style.display = "block";
                statusAlert.className = "alert-box alert-error";
                statusAlert.innerHTML = "❌ <strong>Error:</strong> Failed to connect to server.";
            });
        });
    </script>

</body>
</html>
