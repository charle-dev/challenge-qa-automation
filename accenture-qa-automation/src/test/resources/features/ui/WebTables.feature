@ui
Feature: Web Tables - CRUD
  
  Scenario: Criar, editar e deletar um registro
    Given que acesso a pagina DemoQA
    And seleciono o botao 'Elements'
    And seleciono a opcao 'Web Tables' no menu lateral
    When eu crio um novo registro
    And eu edito o registro criado
    And eu deleto o registro criado
    Then o registro nao deve mais existir
