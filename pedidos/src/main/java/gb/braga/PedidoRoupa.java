public class PedidoRoupa extends Pedido implements Entregavel {
    public PedidoRoupa(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ROUPA);
    }

    @Override
    public double calcularValorFinal() {
        double desconto = (valor > 200) ? (valor * 0.20) : 0;
        return valor - desconto + calcularFrete();
    }

    @Override
    public double calcularFrete() {
        return 10.0 + (valor * 0.05);
    }
}