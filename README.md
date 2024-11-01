# Calculadora-Java-com-Interface-Grafica
 Claro, aqui está um modelo de README para o seu projeto de calculadora:  Calculadora Java com Interface Gráfica Este projeto é uma calculadora desenvolvida em Java, utilizando a biblioteca Swing

# Estrutura do Projeto
O projeto está organizado nas seguintes classes:

Calculadora : Classe principal que inicia a aplicação.

Memoria: Classe que gerencia os cálculos e armazena os dados e operações da calculadora.

MemoriaObservador: Interface que define o padrão Observer, permitindo que os componentes sejam atualizados sempre que o estado da memória muda.

Display: Componente que exibe o valor atual da calculadora.

Botao: Representa cada botão na interface da calculadora, com personalização de cores e estilo.

Teclado: Organiza os botões da calculadora em um painel, incluindo operadores e números.

# Funcionalidades
A calculadora possui as seguintes funcionalidades:

Operações básicas: Soma, subtração, multiplicação e divisão.

Limpar (AC): Limpa o display e reinicia a operação.

Alterar sinal (±): Permite alterar o sinal do número atual.

Operação de igualdade (=): Finaliza a operação e exibe o resultado.

# Captura de Tela

![Calculadora](https://github.com/user-attachments/assets/78cf1633-87d0-4a2f-93ee-7c65f274def7)


# Padrões de Projeto Utilizados


## Singleton

A classe Memoria implementa o padrão Singleton, garantindo que apenas uma única instância dela exista em todo o ciclo de vida da aplicação. Isso é importante para assegurar consistência nos dados manipulados pela calculadora, uma vez que todos os componentes da interface gráfica interagem com essa instância central para atualizar e exibir valores.

## Observer

O padrão Observer é utilizado para notificar o display sempre que o valor na Memoria é atualizado, permitindo que a interface reflita imediatamente as mudanças no estado da calculadora.
