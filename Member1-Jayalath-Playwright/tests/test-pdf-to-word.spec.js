const { test, expect } = require('@playwright/test');
const path = require('path');

test('PDF to Word page elements and initial state', async ({ page }) => {
  // Navigate to the PDF to Word tool
  await page.goto('https://www.pixelssuite.com/pdf-to-word');
  
  // Verify the file dropzone is visible
  await expect(page.locator('text=Drag and drop your file here').first()).toBeVisible({ timeout: 10000 });
  
  // Verify that the conversion button exists
  const convertBtn = page.locator('button', { hasText: /Convert|Word/i }).first();
  await expect(convertBtn).toBeAttached();
});
