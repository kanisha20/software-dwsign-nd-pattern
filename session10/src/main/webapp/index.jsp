<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="society.calculator.MaintenanceCalculator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Society Portal - CI/CD Demo</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f8fafc; max-width: 600px; margin: 50px auto; padding: 20px; }
        .card { background: white; padding: 35px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.06); }
        h1 { color: #1e3a8a; margin-top: 0; }
        .badge-ci { display: inline-block; background: #dcfce7; color: #15803d; padding: 6px 12px; border-radius: 20px; font-weight: bold; font-size: 13px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #e2e8f0; padding: 10px; text-align: left; font-size: 14px; }
        th { background: #f1f5f9; }
    </style>
</head>
<body>

    <div class="card">
        <span class="badge-ci">⚡ CI/CD Automated Build Active</span>
        <h1>🏢 Society Portal Maintenance</h1>
        <p style="color: #64748b;">This application is verified via automated GitHub Actions CI tests.</p>

        <h3>Live Fee Calculation Matrix:</h3>
        <table>
            <thead>
                <tr>
                    <th>Base Rent</th>
                    <th>Days Late</th>
                    <th>Total Due</th>
                    <th>Condition Checked</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>₹ 2,000</td>
                    <td>5 days</td>
                    <td><strong>₹ <%= MaintenanceCalculator.totalDue(2000, 5) %></strong></td>
                    <td>Within grace period (0 fee)</td>
                </tr>
                <tr>
                    <td>₹ 2,000</td>
                    <td>10 days</td>
                    <td><strong>₹ <%= MaintenanceCalculator.totalDue(2000, 10) %></strong></td>
                    <td>On day 10 threshold (0 fee)</td>
                </tr>
                <tr>
                    <td>₹ 2,000</td>
                    <td>11 days</td>
                    <td><strong>₹ <%= MaintenanceCalculator.totalDue(2000, 11) %></strong></td>
                    <td>1 day late (₹50 + ₹10 = ₹60 fee)</td>
                </tr>
                <tr>
                    <td>₹ 2,000</td>
                    <td>40 days</td>
                    <td><strong>₹ <%= MaintenanceCalculator.totalDue(2000, 40) %></strong></td>
                    <td>Capped at ₹300 maximum fee</td>
                </tr>
            </tbody>
        </table>
    </div>

</body>
</html>
