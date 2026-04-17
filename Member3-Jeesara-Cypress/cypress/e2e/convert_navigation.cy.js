/**
 * Cypress Test Suite: Convert Navigation (JPG ↔ PNG, WebP, etc.)
 * Member: Jeesara | Framework: Cypress
 * Target URL: https://www.pixelssuite.com
 */

describe('Convert Tools — Navigation Tests', () => {

  it('TC01 — Homepage loads with PixelsSuite title', () => {
    cy.visit('/');
    cy.title().should('include', 'PixelsSuite');
  });

  it('TC02 — Homepage URL is correct', () => {
    cy.visit('/');
    cy.url().should('include', 'pixelssuite.com');
  });

  it('TC03 — Homepage H1 is visible', () => {
    cy.visit('/');
    cy.get('h1').should('be.visible');
  });

  it('TC04 — Convert to PNG page loads', () => {
    cy.visit('/convert-to-png');
    cy.url().should('include', '/convert-to-png');
    cy.get('h1').should('be.visible');
    cy.screenshot('Convert-to-PNG-page');
  });

  it('TC05 — Convert to PNG heading mentions PNG or Convert', () => {
    cy.visit('/convert-to-png');
    cy.get('h1').invoke('text').then(text => {
      const lower = text.toLowerCase();
      expect(lower.includes('png') || lower.includes('convert')).to.be.true;
    });
  });

  it('TC06 — Convert to PNG has file upload button', () => {
    cy.visit('/convert-to-png');
    cy.get('button').contains(/select files?/i).should('be.visible');
  });

  it('TC07 — Convert to PNG has file input', () => {
    cy.visit('/convert-to-png');
    cy.get('input[type="file"]').should('exist');
  });

  it('TC08 — Navigation between Convert pages works', () => {
    cy.visit('/convert-to-png');
    cy.url().should('include', '/convert-to-png');
    cy.visit('/compress-image');
    cy.url().should('include', '/compress-image');
  });

  it('TC09 — Convert page nav bar is visible', () => {
    cy.visit('/convert-to-png');
    cy.get('nav').should('be.visible');
  });

  it('TC10 — Convert page footer exists', () => {
    cy.visit('/convert-to-png');
    cy.get('footer').should('exist');
  });

  it('TC11 — Mobile view: Convert page loads at 375px', () => {
    cy.viewport(375, 812);
    cy.visit('/convert-to-png');
    cy.get('body').should('be.visible');
    cy.screenshot('Convert-PNG-mobile-view');
  });

  it('TC12 — Directly navigating to homepage from convert page', () => {
    cy.visit('/convert-to-png');
    cy.visit('/');
    cy.url().should('match', /pixelssuite\.com\/?$/);
    cy.screenshot('Navigate-home-from-convert');
  });

});
