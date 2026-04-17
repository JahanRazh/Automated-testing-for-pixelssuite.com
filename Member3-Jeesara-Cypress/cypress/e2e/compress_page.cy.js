describe('Compress Image Page', () => {
  it('renders all required UI elements', () => {
    cy.visit('https://www.pixelssuite.com/compress-image', { failOnStatusCode: false });

    cy.contains('Compress Image').should('be.visible');
    cy.contains('Drag and drop your file here').should('be.visible');
    cy.contains('Select files').should('be.visible');
    cy.get('input[type="file"]').should('exist');
  });
});
