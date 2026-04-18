describe('TC-A08 — Crop PNG Page Verification', () => {
  it('renders Crop PNG page with correct file input', () => {
    cy.visit('https://www.pixelssuite.com/crop-png');
    cy.contains('Crop PNG').should('be.visible');
    cy.contains('Drag and drop your file here').should('be.visible');
    cy.get('input[type="file"]')
      .should('exist')
      .and('have.attr', 'accept', 'image/*');
    cy.log('TC-A08 PASSED');
  });
});
