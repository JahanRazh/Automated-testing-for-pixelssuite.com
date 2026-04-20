const { test, expect } = require('@playwright/test');
const path = require('path');

// Test 1 
// Upload multiple images and verify the result/download area appears
test('PixelsSuite Bulk Resize', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/bulk-resize', {
    waitUntil: 'domcontentloaded',
  });

  const file1 = path.join(__dirname, 'fixtures', 'sample1.png');
  const file2 = path.join(__dirname, 'fixtures', 'sample2.jpg');

  const fileInput = page.locator('input[type="file"]').first();
  await expect(fileInput).toBeAttached({ timeout: 10000 });
  await fileInput.setInputFiles([file1, file2]);

  const widthInput = page
    .locator('input[name="width"], input[placeholder*="Width"], input[aria-label*="Width"]')
    .first();
  if (await widthInput.isVisible().catch(() => false)) {
    await widthInput.fill('800');
  }

  const heightInput = page
    .locator('input[name="height"], input[placeholder*="Height"], input[aria-label*="Height"]')
    .first();
  if (await heightInput.isVisible().catch(() => false)) {
    await heightInput.fill('600');
  }

  const resizeButton = page.getByRole('button', { name: /resize|apply|start/i }).first();
  await expect(resizeButton).toBeVisible({ timeout: 10000 });
  await resizeButton.click();

  const resultArea = page.locator('text=/download|completed|success|done/i').first();
  await expect(resultArea).toBeVisible({ timeout: 20000 });
});

// Test 2
// Upload multiple images → click "Process & Download" → assert ZIP is downloaded
test('Bulk Resize — Process & Download should download a ZIP file', async ({ page }) => {
  await page.goto('https://www.pixelssuite.com/bulk-resize', {
    waitUntil: 'domcontentloaded',
  });

  // 1. Upload two images 
  const file1 = path.join(__dirname, 'fixtures', 'sample1.png');
  const file2 = path.join(__dirname, 'fixtures', 'sample2.jpg');

  const fileInput = page.locator('input[type="file"]').first();
  await expect(fileInput).toBeAttached({ timeout: 10000 });
  await fileInput.setInputFiles([file1, file2]);

  // 2. Set dimensions if visible 
  const widthInput = page
    .locator('input[name="width"], input[placeholder*="Width"], input[aria-label*="Width"]')
    .first();
  if (await widthInput.isVisible().catch(() => false)) {
    await widthInput.fill('800');
  }

  const heightInput = page
    .locator('input[name="height"], input[placeholder*="Height"], input[aria-label*="Height"]')
    .first();
  if (await heightInput.isVisible().catch(() => false)) {
    await heightInput.fill('600');
  }

  // 3. Click "Process & Download"
  const processDownloadButton = page
    .getByRole('button', { name: /process\s*&\s*download/i })
    .first();
  await expect(processDownloadButton).toBeVisible({ timeout: 10000 });

  //  4. Collect all download events 
  const downloads = [];
  page.on('download', (dl) => downloads.push(dl));

  await processDownloadButton.click();
  await page.waitForTimeout(15000);

  // 5. Assert at least one download occurred 
  expect(downloads.length).toBeGreaterThanOrEqual(1);

  // 6. Assert the download is a ZIP file 
  const filenames = downloads.map((dl) => dl.suggestedFilename().toLowerCase());
  const zipFile = filenames.find((name) => name.endsWith('.zip'));

  expect(
    zipFile,
    `Expected a .zip download but got: [${filenames.join(', ')}]`
  ).toBeTruthy();
});