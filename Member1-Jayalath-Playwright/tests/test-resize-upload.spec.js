// @ts-check
const { test, expect } = require('@playwright/test');

test('Uploading image activates Resize options panel', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/resize-image');

  // Use an inline minimal 1×1 JPEG buffer — no fixture file dependency needed.
  // setInputFiles() accepts {name, mimeType, buffer} directly in Playwright.
  const minimalJpeg = Buffer.from(
    '/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoH' +
    'BwYIDAoMCwsKCwsNCxAQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/' +
    'wAALCAABAAEBAREA/8QAFAABAAAAAAAAAAAAAAAAAAAACf/EABQQAQAAAAAAAAAAAAAAAAAAAAD/' +
    '2gAIAQEAAT8AVAD/2Q==',
    'base64'
  );

  const fileInput = page.locator('input[type="file"]');
  await fileInput.setInputFiles({
    name: 'sample.jpg',
    mimeType: 'image/jpeg',
    buffer: minimalJpeg,
  });

  // Placeholder should disappear once image is loaded
  await expect(
    page.locator('text=Select an image to configure size')
  ).not.toBeVisible();
});
