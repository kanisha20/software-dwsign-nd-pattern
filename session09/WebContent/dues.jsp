<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  TASK 3: dues.jsp (Dues Viewer using your own API)
  - Calls /api/dues?flatNo=... via fetch() and response.json()
  - Builds table rows dynamically using `document.createElement`
  - Shows 'No dues found' for an unknown flat
  - Tests: A-101 (3 monthly rows), B-201 (1 row), unknown flat (no dues)
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SocietyPay - Dues Viewer API</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f1f5f9; max-width: 650px; margin: 40px auto; padding: 20px; }
        .card { background: white; padding: 32px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.06); }
        h2 { color: #1e3a8a; margin-top: 0; text-align: center; }
        .input-group { display: flex; gap: 10px; margin-top: 20px; }
        input[type="text"] { flex: 1; padding: 12px; border: 1px solid #cbd5e1; border-radius: 6px; font-size: 15px; }
        button { background: #2563eb; color: white; border: none; padding: 12px 24px; border-radius: 6px; cursor: pointer; font-size: 15px; font-weight: bold; transition: background 0.2s; }
        button:hover { background: #1d4ed8; }
        table { width: 100%; border-collapse: collapse; margin-top: 25px; display: none; }
        th, td { border: 1px solid #e2e8f0; padding: 12px; text-align: left; }
        th { background: #f8fafc; color: #475569; font-size: 14px; }
        td { font-size: 14px; }
        .no-data { margin-top: 25px; padding: 15px; background: #fef2f2; color: #991b1b; border: 1px solid #fecaca; border-radius: 6px; text-align: center; font-weight: bold; display: none; }
        .total-row { background: #eff6ff; font-weight: bold; }
        .quick-buttons { margin-top: 12px; font-size: 13px; color: #64748b; }
        .quick-btn { background: #e2e8f0; border: none; padding: 4px 10px; border-radius: 4px; cursor: pointer; margin-left: 5px; font-size: 12px; }
        .quick-btn:hover { background: #cbd5e1; }
    </style>
</head>
<body>

    <div class="card">
        <h2>📊 Maintenance Dues Viewer</h2>
        <p style="text-align: center; color: #64748b; font-size: 14px; margin-top: -5px;">Fetch API + Dynamic DOM Rendering via <code>document.createElement</code></p>

        <div class="input-group">
            <input type="text" id="flatNoInput" placeholder="Enter Flat No (e.g. A-101)" value="A-101">
            <button id="btnShowDues">🔍 Show Dues</button>
        </div>

        <div class="quick-buttons">
            Quick Test:
            <button class="quick-btn" onclick="setFlat('A-101')">A-101 (3 Months)</button>
            <button class="quick-btn" onclick="setFlat('B-201')">B-201 (1 Month)</button>
            <button class="quick-btn" onclick="setFlat('Z-999')">Z-999 (Unknown)</button>
        </div>

        <!-- Dynamic Dues Table -->
        <table id="duesTable">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Billing Month</th>
                    <th>Maintenance Amount</th>
                </tr>
            </thead>
            <tbody id="duesBody"></tbody>
        </table>

        <!-- No dues found message (Constraint: exact phrase 'No dues found') -->
        <div id="noDuesAlert" class="no-data">
            ⚠️ No dues found
        </div>

        <p style="text-align: center; margin-top: 30px; font-size: 14px;">
            <a href="register.jsp" style="color: #2563eb; text-decoration: none; font-weight: bold;">← Back to Resident Registration</a>
        </p>
    </div>

    <script>
        function setFlat(val) {
            document.getElementById('flatNoInput').value = val;
            document.getElementById('btnShowDues').click();
        }

        document.getElementById('btnShowDues').addEventListener('click', function () {
            var flatNo = document.getElementById('flatNoInput').value.trim();
            var table = document.getElementById('duesTable');
            var tbody = document.getElementById('duesBody');
            var noDuesAlert = document.getElementById('noDuesAlert');

            // Reset view
            tbody.innerHTML = "";
            table.style.display = "none";
            noDuesAlert.style.display = "none";

            if (flatNo === "") {
                noDuesAlert.style.display = "block";
                return;
            }

            // ============================================================
            // Task 3: Call /api/dues?flatNo=... with fetch() & response.json()
            // ============================================================
            fetch("${pageContext.request.contextPath}/api/dues?flatNo=" + encodeURIComponent(flatNo))
                .then(function (response) {
                    return response.json();
                })
                .then(function (duesList) {
                    if (!duesList || duesList.length === 0) {
                        // Constraint: Show 'No dues found' for unknown flat or empty list
                        noDuesAlert.style.display = "block";
                    } else {
                        var total = 0;

                        // Constraint: Build table rows using document.createElement
                        duesList.forEach(function (due, index) {
                            var tr = document.createElement("tr");

                            var tdIdx = document.createElement("td");
                            tdIdx.textContent = (index + 1);
                            tr.appendChild(tdIdx);

                            var tdMonth = document.createElement("td");
                            tdMonth.textContent = due.month;
                            tr.appendChild(tdMonth);

                            var tdAmount = document.createElement("td");
                            tdAmount.innerHTML = "<strong>₹ " + due.amount.toFixed(2) + "</strong>";
                            tr.appendChild(tdAmount);

                            total += due.amount;
                            tbody.appendChild(tr);
                        });

                        // Append Total Row
                        var trTotal = document.createElement("tr");
                        trTotal.className = "total-row";

                        var tdTotalLabel = document.createElement("td");
                        tdTotalLabel.colSpan = 2;
                        tdTotalLabel.style.textAlign = "right";
                        tdTotalLabel.textContent = "Total Outstanding Dues:";
                        trTotal.appendChild(tdTotalLabel);

                        var tdTotalVal = document.createElement("td");
                        tdTotalVal.textContent = "₹ " + total.toFixed(2);
                        trTotal.appendChild(tdTotalVal);

                        tbody.appendChild(trTotal);
                        table.style.display = "table";
                    }
                })
                .catch(function (error) {
                    noDuesAlert.style.display = "block";
                    noDuesAlert.textContent = "Error fetching dues API.";
                });
        });
    </script>

</body>
</html>
