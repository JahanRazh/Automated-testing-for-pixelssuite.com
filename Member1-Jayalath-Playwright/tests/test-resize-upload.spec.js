const { test, expect } = require('@playwright/test');
const path = require('path');

test('TC-A02: File upload activates Resize options panel', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/resize-image');
  // pointer-events:none blocks click — inject file directly into input
  const fileInput = page.locator('input[type="file"]');
  await fileInput.setInputFiles(path.join(__dirname, '../fixtures/sample.jpg'));
  await expect(
    page.locator('text=Select an image to configure size')
  ).not.toBeVisible({ timeout: 8000 });
  console.log('TC-A02 PASSED');
});
