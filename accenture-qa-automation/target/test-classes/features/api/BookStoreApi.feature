@api
Feature: BookStore API - Fluxo completo

  Scenario: Criar usuario, autorizar, reservar dois livros e validar
    Given que eu crio um usuario via API
    And eu gero um token de acesso
    And eu confirmo que o usuario esta autorizado
    And eu listo os livros e escolho dois
    When eu reservo os dois livros para o usuario
    Then os detalhes do usuario exibem os dois livros
