const { test, expect } = require('@playwright/test');

test('TC-A01: Resize Image page loads with all UI elements', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/resize-image');
  await expect(page).toHaveTitle(/PixelsSuite/);
  await expect(page.locator('text=Drag and drop your file here')).toBeVisible();
  await expect(page.locator('input[type="file"]')).toHaveCount(1);
  await expect(page.locator('text=Resize Image').first()).toBeVisible();
  console.log('TC-A01 PASSED');
});

