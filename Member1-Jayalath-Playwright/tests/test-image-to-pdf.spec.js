const { test, expect } = require('@playwright/test');
const path = require('path');

test('TC-A03: Image to PDF page elements and button state', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/image-to-pdf');
  await expect(page.locator('text=Drag and drop your file here')).toBeVisible();
  await expect(page.locator('text=Selected Images').first()).toBeVisible();
  const btn = page.locator('button', { hasText: 'Create PDF' });
  await expect(btn).toBeDisabled();
  console.log('TC-A03 PASSED');
});
