/**
 * Cypress Test Suite: GIF Compressor Page
 * Member: Jeesara | Framework: Cypress
 * Target URL: https://www.pixelssuite.com/gif-compressor
 */

describe('GIF Compressor Page — UI Tests', () => {

  beforeEach(() => {
    cy.visit('/gif-compressor');
  });

  it('TC01 — GIF Compressor page loads with PixelsSuite title', () => {
    cy.title().should('include', 'PixelsSuite');
  });

  it('TC02 — URL contains /gif-compressor', () => {
    cy.url().should('include', '/gif-compressor');
  });

  it('TC03 — H1 heading is visible and contains GIF or Compress', () => {
    cy.get('h1')
      .should('be.visible')
      .invoke('text')
      .then(text => {
        const lower = text.toLowerCase();
        expect(lower.includes('gif') || lower.includes('compress')).to.be.true;
      });
  });

  it('TC04 — Select Files button is visible', () => {
    cy.get('button').contains(/select files?/i).should('be.visible');
  });

  it('TC05 — File input element is present', () => {
    cy.get('input[type="file"]').should('exist');
  });

  it('TC06 — File input accepts GIF files', () => {
    cy.get('input[type="file"]').then($input => {
      const accept = $input.attr('accept') || '';
      const lower = accept.toLowerCase();
      // Should accept gif or image/* or all files
      const isAccepted = lower.includes('gif') || lower.includes('image') || accept === '';
      expect(isAccepted).to.be.true;
    });
  });

  it('TC07 — Nav bar is present', () => {
    cy.get('nav').should('be.visible');
  });

  it('TC08 — Page contains GIF-related content text', () => {
    cy.get('body').invoke('text').then(text => {
      expect(text.toLowerCase()).to.include('gif');
    });
  });

  it('TC09 — Footer exists on GIF compressor page', () => {
    cy.get('footer').should('exist');
  });

  it('TC10 — Screenshot of GIF Compressor page', () => {
    cy.screenshot('gif-compressor-page');
  });

  it('TC11 — Mobile viewport: GIF compressor still shows H1', () => {
    cy.viewport(375, 812);
    cy.visit('/gif-compressor');
    cy.get('h1').should('be.visible');
    cy.screenshot('gif-compressor-mobile');
  });

  it('TC12 — Can navigate from GIF compressor to Compress Image page', () => {
    cy.visit('/gif-compressor');
    cy.visit('/compress-image');
    cy.url().should('include', '/compress-image');
    cy.get('h1').should('be.visible');
    cy.screenshot('gif-to-compress-navigation');
  });

});
