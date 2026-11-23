# Q10 — Introduction to CI/CD & Deployment Workflow (`society-portal-ci`)

## 📁 Project Overview
Is project mein Maven, JUnit 5 aur **GitHub Actions CI/CD Pipeline** implement kiya gaya hai jo har git push par automate build, test aur deployable WAR artifact packaging perform karta hai.

---

### 🛠️ Implemented Tasks & Features

1. **Task 1: Tested Class & JUnit 5 Suite (`MaintenanceCalculator.java`)**:
   - Class: `society.calculator.MaintenanceCalculator`
   - Method: `totalDue(int baseAmount, int daysLate)`
     - Days 0 to 10: Late fee is ₹0 (Grace period).
     - Beyond Day 10: ₹50 base late fee + ₹10 per each additional day.
     - Maximum Cap: Late fee cannot exceed ₹300.
     - Negative input check: Throws `IllegalArgumentException`.
   - **5 Automated JUnit Tests in `MaintenanceCalculatorTest.java`**:
     1. `(2000, 5)` -> `2000`
     2. `(2000, 10)` -> `2000`
     3. `(2000, 11)` -> `2060`
     4. `(2000, 40)` -> `2300` (capped)
     5. `(2000, -1)` -> throws `IllegalArgumentException`

2. **Task 2: GitHub Actions CI Pipeline (`.github/workflows/ci.yml`)**:
   - Triggers on push to `main` branch.
   - Code checkout with `actions/checkout@v4`.
   - Sets up Java 17 Temurin using `actions/setup-java@v4`.
   - Runs automated tests: `mvn -B test`.
   - `.gitignore` ignores `target/` and temporary IDE files.

3. **Task 3: Automated Packaging & Quality Gate (`actions/upload-artifact@v4`)**:
   - `<finalName>society-portal-ci</finalName>` builds `target/society-portal-ci.war`.
   - **Automated Gate**: Agar koi bhi unit test fail hota hai, pipeline turant fail (Red) ho jayegi aur koi WAR artifact upload nahi hoga.
   - All tests passing -> Pipeline Green -> Artifact `society-portal-ci.war` downloadable tab mein upload ho jata hai.

---

## 🚀 Step-by-Step GitHub Setup Guide

Terminal open karein aur is project directory par jaayein:
```bash
cd "C:\Users\dipak\Desktop\project 2\Q10_CICD_Workflow\society-portal-ci"
```

### Git Initialize aur First Commit:
```bash
git init
git add .
git commit -m "feat: complete society-portal-ci with JUnit tests and GitHub Actions"
git branch -M main
```

### GitHub Repo Link aur Push:
1. GitHub par jaakar naya repository banayein: `society-portal-ci`.
2. Commands run karein:
```bash
git remote add origin https://github.com/<YOUR_USERNAME>/society-portal-ci.git
git push -u origin main
```

3. GitHub repository ke **Actions** tab par jayein -> Aapko **Green Checkmark** dikhega aur `society-portal-ci-war` artifact download ke liye available hoga!
