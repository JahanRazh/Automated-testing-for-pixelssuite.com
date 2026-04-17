// @ts-check
const { test, expect } = require('@playwright/test');

/**
 * Test Suite: Image Enlarger Page
 * Member: Jayalath | Framework: Playwright
 * Tests: Page load, heading, UI elements, navigation from menu
 */

test('Image Enlarger page elements present', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/image-enlarger');

  await expect(page.locator('text=Image Enlarger').first()).toBeVisible();
  await expect(page.locator('text=Drag and drop your file here')).toBeVisible();
  await expect(page.locator('text=Enlarge').first()).toBeVisible();
  await expect(page.locator('text=Preview').first()).toBeVisible();
});
