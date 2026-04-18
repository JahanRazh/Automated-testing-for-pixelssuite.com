// ***********************************************************
// cypress/support/e2e.js
// This file is loaded automatically before each test suite.
// ***********************************************************

// Add custom commands or global configuration here.
// Example:
// Cypress.Commands.add('selectFile', (filePath) => { ... });

// Global error handler - ignore uncaught exceptions from the target site
Cypress.on('uncaught:exception', (err, runnable) => {
  // Prevent Cypress from failing on uncaught app exceptions
  return false;
});
