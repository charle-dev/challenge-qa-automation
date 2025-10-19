@ui
Feature: Progress Bar - Controle preciso
  
  Scenario: Parar antes de 25, validar e resetar
    Given que acesso a pagina DemoQA
    And seleciono o botao 'Widgets'
    And seleciono a opcao 'Progress Bar' no menu lateral
    When eu inicio e paro aos 25 por cento
    Then a barra deve estar menor ou igual a 25 por cento
    When eu inicio novamente ate 100 por cento e reseto
