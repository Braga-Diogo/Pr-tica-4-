public class PedidoAlimento extends Pedido {
    public PedidoAlimento(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Catehoria.ALIMENTO);
    }

    @Override
    public double calcularValorFinal() {
        // Sem desconto e frete fixo de 5 adicionado diretamente
        return valor + 5.0;
    }
}