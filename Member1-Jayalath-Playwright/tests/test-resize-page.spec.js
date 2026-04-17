// @ts-check
const { test, expect } = require('@playwright/test');

/**
 * Test Suite: Image Resize Page — UI & Navigation
 * Member: Jayalath | Framework: Playwright
 * Tests: Page load, heading, upload area visibility, nav links
 */

test('Resize Image page loads with upload zone', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/resize-image');

  await expect(page).toHaveTitle(/PixelsSuite/);
  await expect(page.locator('text=Drag and drop your file here')).toBeVisible();
  await expect(page.locator('input[type="file"]')).toHaveCount(1);
});

