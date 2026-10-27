public abstract class Pedido implements Processavel {

    protected int id;
    protected String nomeCliente;
    protected double valor;
    protected StatusPedido status;
    protected Categoria categoria;

    public Pedido(int id, String nomeCliente, double valor, Categoria categoria) {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do pedido deve ser superior a zero.");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.status = StatusPedido.PENDENTE;
        this.categoria = categoria;
    }

    public void alterarStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        if(this.status == StatusPedido.ENTREGUE) {
            throw new PedidoInvalidoException("Um pedido entregue não pode mudar de status.");
        }
        this.status = novoStatus;
    }

    @Override
    public void processarPedido() throws PedidoInvalidoException {
        if (this.status == StatusPedido.CANCELADO){
            throw new PedidoInvalidoException("Um pedido cancelado não pode ser processado.");
        }
        this.status = StatusPedido.PROCESSANDO;
    }

    public StatusPedido getStatus() { return status; }
    public int getId() { return id; }

    @Override
    public String toString() {
        return "Pedido ID: " + id + " | Cliente: " + nomeCliente + " | Categoria: " + categoria
                + " | Status: " + status + " | Valor Final:  R$" + String.format("%.2f", calcularValorFinal());
    }

}