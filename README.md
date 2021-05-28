# SIMUSHOOP
Simulador de plataforma e-commerce

SIMUSHOOP

Aplicação: 
	A aplicação consiste em uma plataforma que tem como objetivo simular um ambiente digital de comprar e vendas de produtos. A aplicação utilizada a abordagem cliente-servidor, no qual foi implementado usando a linguagem de programação java.  Então a ideia consiste em possíveis vendedores e consumidores se conectarem no servidor e então realizarem suas inscrições. Desta forma os vendedores poderiam adicionar seus produtos a venda, através da aplicação, e analogamente os clientes verificam os produtos à venda e realizam compras.

Principais funcionalidades:
**	Cadastrado de novos clientes (Vendedores/Consumidores)
**	Login
**	Adição de produtos pelos clientes cadastrados na forma de vendedor.
**	Vendedores podem visualizar possíveis ganhos com vendas
**	Busca de produtos por clientes Consumidores

O que poderia ser implementado a mais?
** A ideia inicial era desenvolver uma interface gráfica simples qual os clientes poderiam interagir de forma mais fluida. Entretanto do desenvolvimento da mesma mostrou se torna um quanto complexa, oque poderiam necessitar de um tempo maior, além da falta de domínio no desenvolvimento dessa ideia tornou a implementação inviável.
**	Interface de usuário mais fluida
**	Remoção de produtos
**	Gerenciamento de compras mais robusta

Dificuldades:
**	Adoção de uma padronização de comunicação entre aplicação
**	Desenvolvimento da UI







INSTRUÇÕES PARA EXECUTAR A APLICAÇÃO:
1.	Na pasta ServeApp execute o arquivo Server.java, na maquina que funcionara como servidor da aplicação.

2.	Na pasta ClientApp existe apenas um arquivo execute-o na máquina que atuara como cliente. 

OBS:	Por default o endereço servidor é configurado como “localhost” na aplicação cliente (ClientMain.java) se o arquivo ServerApp está rodando em outra máquina remota, alterar a linha 19 do arquivo ClientMain.java, adicionando o endereço do servidor.
