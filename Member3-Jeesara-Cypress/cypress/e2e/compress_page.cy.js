/**
 * Cypress Test Suite: Image Compressor Page
 * Member: Jeesara | Framework: Cypress
 * Target URL: https://www.pixelssuite.com/compress-image
 */

describe('Image Compressor Page — UI Tests', () => {

  beforeEach(() => {
    cy.visit('/compress-image');
  });

  it('TC01 — Page loads with correct title', () => {
    cy.title().should('include', 'PixelsSuite');
  });

  it('TC02 — URL contains /compress-image', () => {
    cy.url().should('include', '/compress-image');
  });

  it('TC03 — H1 heading contains "Compress"', () => {
    cy.get('h1')
      .should('be.visible')
      .and('contain.text', 'Compress');
  });

  it('TC04 — File upload button is visible', () => {
    cy.get('button')
      .contains(/select files?/i)
      .should('be.visible')
      .and('not.be.disabled');
  });

  it('TC05 — Hidden file input exists in DOM', () => {
    cy.get('input[type="file"]').should('exist');
  });

  it('TC06 — Navigation bar is visible', () => {
    cy.get('nav').should('be.visible');
  });

  it('TC07 — "Compress" appears in the nav menu', () => {
    cy.get('nav').should('contain.text', 'Compress');
  });

  it('TC08 — Page source mentions supported formats (PNG, JPG, WEBP)', () => {
    cy.get('body').invoke('text').then((text) => {
      const lower = text.toLowerCase();
      const mentionsFormat = lower.includes('png') || lower.includes('jpg') || lower.includes('webp');
      expect(mentionsFormat).to.be.true;
    });
  });

  it('TC09 — Footer is present on compress page', () => {
    cy.get('footer').should('exist');
  });

  it('TC10 — Screenshot of compress page', () => {
    cy.screenshot('compress-page-loaded');
  });

  it('TC11 — Page handles 768px viewport correctly', () => {
    cy.viewport(768, 1024);
    cy.get('h1').should('be.visible');
    cy.screenshot('compress-tablet-viewport');
  });

  it('TC12 — Page handles 375px mobile viewport', () => {
    cy.viewport(375, 812);
    cy.get('body').should('be.visible');
    cy.screenshot('compress-mobile-viewport');
  });

});
