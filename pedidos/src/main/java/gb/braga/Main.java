package gb.braga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Pedido> pedidos = new ArrayList<>();
        int geradorId = 1;

        while (true) {
            System.out.println("\n--- Sistema Gerenciador de Pedidos ---");
            System.out.println("1. Cadastrar pedido");
            System.out.println("2. Alterar o status de um pedido");
            System.out.println("4. Remover cancelados");
            System.out.println("5. Exibir lista de pedidos");
            System.out.println("6. Exibir pedidos entregáveis");
            System.out.println("7. Encerrar");
            System.out.println("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 7) {
                System.out.println("Encerrado o sistema...");
                break;
            }

            switch (opcao) {
                case 1:
                    System.out.println("Nome do Cliente: ");
                    String nome = scanner.nextLine();
                    System.out.println("Valor do Pedido: ");
                    double valor = scanner.nextDouble();
                    System.out.println("1-Eletrónico, 2-Roupa, 3-Alimento): ");
                    int cat = scanner.nextInt();

                    try {
                        Pedido p = null;
                        if (cat == 1) p = new PedidoEletronico(geradorId++, nome, valor);
                        else if (cat == 2) p = new PedidoRoupa(geradorId++, nome, valor);
                        else if (cat == 3) p = new PedidoAlimento(geradorId++, nome, valor);
                        else System.out.println("Categoria inválida!");

                        if (p != null) {
                            pedidos.add(p);
                            System.out.println("Pedido cadastrado com sucesso! ID: " + p.getId());
                        }
                    } catch (ValorInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("ID do Pedido: ");
                    int idAlterar = scanner.nextInt();
                    System.out.println("Novo Status (1-PENDENTE, 2-PROCESSANDO, 3-ENVIADO, 4-ENTREGUE, 5-CANCELADO): ");
                    int statId = scanner.nextInt();
                    
                    if (statId >= 1 && statId <= 5) {
                        StatusPedido novoStatus = StatusPedido.values()[statId - 1];
                        for (Pedido p : pedidos) {
                            if (p.getId() == idAlterar) {
                                try {
                                    p.alterarStatus(novoStatus);
                                    System.out.println("Status alterado com sucesso.");
                                } catch (PedidoInvalidoException e) {
                                    System.out.println("Erro: " + e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                    break;

                case 3:
                    Iterator<Pedido> itProcessar = pedidos.iterator();
                    while (itProcessar.hasNext()) {
                        Pedido p = itProcessar.next();
                        try {
                            p.processarPedido();
                        } catch (PedidoInvalidoException e) {
                            System.out.println("Aviso ao processar Pedido " + p.getId() + ": " + e.getMessage());
                        }
                    }
                    System.out.println("Ciclo de processamento concluído.");
                    break;

                case 4:
                    Iterator<Pedido> itRemover = pedidos.iterator();
                    while (itRemover.hasNext()) {
                        if (itRemover.next().getStatus() == StatusPedido.CANCELADO) {
                            itRemover.remove();
                        }
                    }
                    System.out.println("Pedidos cancelados removidos da base.");
                    break;

                case 5:
                    for (Pedido p : pedidos) {
                        if (p.getStatus() != StatusPedido.CANCELADO) {
                            System.out.println(p.toString());
                        }
                    }
                    break;

                case 6:
                    for (Pedido p : pedidos) {
                        if (p instanceof Entregavel) {
                            double frete = ((Entregavel) p).calcularFrete();
                            System.out.println(p.toString() + " | Valor de Frete: R$" + String.format("%.2f", frete));
                        }
                    }
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
     }
   }
