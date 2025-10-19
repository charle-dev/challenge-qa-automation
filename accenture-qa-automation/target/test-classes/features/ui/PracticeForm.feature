@ui
Feature: Practice Form - Preenchimento e validacao
  
  Scenario: Preencher o formulario, enviar e validar popup
    Given que acesso a pagina DemoQA
    And seleciono o botao 'Forms'
    And seleciono a opcao 'Practice Form' no menu lateral
    When eu preencho o formulario com dados validos
    And eu faco upload do arquivo de texto
    And eu submeto o formulario
    Then o popup de confirmacao e exibido
    And eu fecho o popup
