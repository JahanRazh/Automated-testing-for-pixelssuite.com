# 🖼️ PixelsSuite Automated Testing — Group Project

> **Target Site:** https://www.pixelssuite.com  
> **Course:** 4th Year, Semester 2 — Software Quality Assurance  

---

## 👥 Team Members & Frameworks

| Member | Framework | Language | Folder |
|--------|-----------|----------|--------|
| **Jayalath** | Playwright | JavaScript | `Member1-Jayalath-Playwright/` |
| **Jeesara** | Selenium WebDriver | Java (Maven + TestNG) | `Member2-Jeesara-Selenium/` |
| **Reshmi** | Cypress | JavaScript | `Member3-Reshmi-Cypress/` |
| **Pathum** | TestNG + Selenium | Java (Maven) | `Member4-Pathum-TestNG/` |

---

## 🌐 Tested Pages (URL Routes)

| Tool | URL |
|------|-----|
| Homepage | `/` |
| Image Resizer | `/resize-image` |
| Image Enlarger | `/image-enlarger` |
| Image Compressor | `/compress-image` |
| Crop Image | `/crop-image` |
| GIF Compressor | `/gif-compressor` |
| Image to PDF | `/image-to-pdf` |
| OCR (Image to Text) | `/image-to-text` |
| Color Picker | `/color-picker` |
| Convert to PNG | `/convert-to-png` |

---

## 🎭 Member 1 — Jayalath | Playwright

### Prerequisites
- Node.js 18+ → https://nodejs.org

### Setup
```bash
cd Member1-Jayalath-Playwright
npm install
npx playwright install
```

### Run Tests
```bash
# Run all tests
npx playwright test

# Run with browser visible
npx playwright test --headed

# Run one file
npx playwright test tests/test-resize-page.spec.js

# Generate HTML report
npx playwright test --reporter=html
npx playwright show-report
```

---

## 🔵 Member 2 — Jeesara | Selenium WebDriver (Java)

### Prerequisites
- Java JDK 11+ → https://adoptium.net
- Maven → https://maven.apache.org
- Chrome browser installed

### Setup
```bash
cd Member2-Jeesara-Selenium
mvn test
```

### Run Tests
```bash
# Run all tests
mvn test

# Run one class
mvn test -Dtest=CropJpgPageTest

# View results → target/surefire-reports/index.html
```

---

## 🌲 Member 3 — Reshmi | Cypress

### Prerequisites
- Node.js 18+ → https://nodejs.org

### Setup
```bash
cd Member3-Reshmi-Cypress
npm install
```

### Run Tests
```bash
# Open interactive GUI (recommended for demo)
npx cypress open

# Run all tests headless
npx cypress run

# Run one file
npx cypress run --spec "cypress/e2e/compress_page.cy.js"

# Run with Chrome
npx cypress run --browser chrome
```

---

## 🔴 Member 4 — Pathum | TestNG + Selenium

### Prerequisites
- Java JDK 11+ → https://adoptium.net
- Maven → https://maven.apache.org
- Chrome browser installed

### Setup & Run
```bash
cd Member4-Pathum-TestNG

# Run all tests via testng.xml suite
# mvn test -DsuiteXmlFile=testng.xml
mvn test "-DsuiteXmlFile=testng.xml"

# View HTML report → target/surefire-reports/index.html
```

---

## 📸 Screenshots

**Playwright** — auto-saved in `/playwright-report/`

**Selenium / TestNG** — add inside any `@Test`:
```java
TakesScreenshot ts = (TakesScreenshot) driver;
File src = ts.getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(src, new File("screenshots/test-name.png"));
```

**Cypress** — auto-saved on failure, or manually:
```javascript
cy.screenshot('test-name');
```
