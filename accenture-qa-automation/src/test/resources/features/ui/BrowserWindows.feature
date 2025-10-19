@ui
Feature: Browser Windows - Nova janela
  
  Scenario: Abrir nova janela e validar mensagem
    Given que acesso a pagina DemoQA
    And seleciono o botao 'Alerts, Frame & Windows'
    And seleciono a opcao 'Browser Windows' no menu lateral
    When eu clico no botao New Window
    Then uma nova janela e aberta com a mensagem correta
    And eu fecho a nova janela
