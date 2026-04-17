const { test, expect } = require('@playwright/test');
const path = require('path');

test('PixelsSuite Bulk Resize', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/bulk-resize', {
    waitUntil: 'domcontentloaded',
  });

  const file1 = path.join(__dirname, 'fixtures', 'sample1.png');
  const file2 = path.join(__dirname, 'fixtures', 'sample2.jpg');

  const fileInput = page.locator('input[type="file"]').first();
  await expect(fileInput).toBeAttached({ timeout: 10000 });
  await fileInput.setInputFiles([file1, file2]);

  const widthInput = page.locator('input[name="width"], input[placeholder*="Width"], input[aria-label*="Width"]').first();
  if (await widthInput.isVisible().catch(() => false)) {
    await widthInput.fill('800');
  }

  const heightInput = page.locator('input[name="height"], input[placeholder*="Height"], input[aria-label*="Height"]').first();
  if (await heightInput.isVisible().catch(() => false)) {
    await heightInput.fill('600');
  }

  const resizeButton = page.getByRole('button', { name: /resize|apply|start/i }).first();
  await expect(resizeButton).toBeVisible({ timeout: 10000 });
  await resizeButton.click();

  const resultArea = page.locator('text=/download|completed|success|done/i').first();
  await expect(resultArea).toBeVisible({ timeout: 20000 });
});