describe('Image Converter Navigation', () => {
  it('navigates from homepage to Convert Image (To JPG)', () => {
    cy.visit('https://www.pixelssuite.com/');

    cy.contains('button', 'Image Converter').click();
    cy.contains('To JPG').first().click();

    cy.contains('Convert Image').should('be.visible');
    cy.get('input[type="file"]').should('exist');
  });
});
