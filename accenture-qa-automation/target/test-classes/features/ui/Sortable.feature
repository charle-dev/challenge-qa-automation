@ui
Feature: Sortable - Ordem decrescente
  
  Scenario: Reordenar lista para ordem decrescente
    Given que acesso a pagina DemoQA
    And seleciono o botao 'Interactions'
    And seleciono a opcao 'Sortable' no menu lateral
    When eu reordeno a lista em ordem decrescente
    Then a lista deve estar em ordem decrescente
