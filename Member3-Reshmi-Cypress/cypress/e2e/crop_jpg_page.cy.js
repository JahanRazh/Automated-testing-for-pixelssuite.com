describe('TC-A07 — Crop JPG Page Elements', () => {
  it('renders heading, drop zone, and panels', () => {
    cy.visit('https://www.pixelssuite.com/crop-jpg');
    cy.contains('Crop JPG').should('be.visible');
    cy.contains('Drag and drop your file here').should('be.visible');
    cy.contains('Select files').should('be.visible');
    cy.get('input[type="file"]').should('exist');
    cy.contains('Preview').should('exist');
    cy.log('TC-A07 PASSED');
  });
});
