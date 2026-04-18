describe('GIF Compressor Page', () => {
  it('renders heading and upload button', () => {
    cy.visit('https://www.pixelssuite.com/gif-compressor', { failOnStatusCode: false });

    cy.contains('GIF Compressor').should('be.visible');
    cy.contains('Select GIF').should('be.visible');

    // BUG: accept is image/* instead of image/gif — any image file accepted
    cy.get('input[type="file"]').should('have.attr', 'accept', 'image/*');
  });
});
