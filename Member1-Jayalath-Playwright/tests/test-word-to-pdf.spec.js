const { test, expect } = require('@playwright/test');
const path = require('path');

test('Word to PDF page elements and initial state', async ({ page }) => {
  // Navigate to the Word to PDF tool
  await page.goto('https://www.pixelssuite.com/word-to-pdf');
  
  // Verify the file dropzone is visible
  await expect(page.locator('text=Drag and drop your file here').first()).toBeVisible({ timeout: 10000 });
  
  // Verify that the conversion button exists
  const convertBtn = page.locator('button', { hasText: /Convert|PDF/i }).first();
  await expect(convertBtn).toBeAttached();
});
