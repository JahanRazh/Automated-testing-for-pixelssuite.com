const { test, expect } = require('@playwright/test');
const path = require('path');

test('Uploading image activates Resize options panel', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/resize-image');

  // Buttons have pointer-events:none — inject file directly into input
  const fileInput = page.locator('input[type="file"]');
  await fileInput.setInputFiles(path.join(__dirname, '../fixtures/sample.jpg'));

  // Placeholder should disappear once image is loaded
  await expect(
    page.locator('text=Select an image to configure size')
  ).not.toBeVisible();
});
