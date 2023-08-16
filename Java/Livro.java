// View
class FrCadLivro () {
    ...

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (idLivroEditando > 0) {
                livroController.atualizarLivro(idLivroEditando, edtTitulo.getText(), edtAutor.getText(), edtNumPag.getText(), edtPreco.getText());
            } else {
                livroController.cadastrarLivro(edtTitulo.getText(), edtAutor.getText(), edtNumPag.getText(), edtPreco.getText());
            }
            // Comando bastante importante
            this.idLivroEditando = -1;

            livroController.atualizarTabela(grdLivros);

            this.habilitarCampos(false);
            this.limparCampos();
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
        } catch (LivroException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}

// Controller
LivroDao repositorio;
public saveLivro(String titulo, String autor, int numPag, Double preco) {
    ValidateLivro valid = new ValidateLivro();
    Livro livroOk = valid.validarLivro(titulo, autor, numPag, preco);

    if (livroOk == null)
        throw new LivroException("Livro com problema");
    else
        repositorio.save(livroOk);
}

// Model
public class LivroDAO implements IDao {
    ...
    @Override
    public void save(Object obj) {
        Livro livro = (Livro) obj;

        sql = " INSERT INTO "
                + " livro(titulo, autor, numPag, preco) "
                + " VALUES(?,?,?,?,?,?) ";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);

            // Preencher cada ? com o campo adequado
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getAutor());
            statement.setInt(3, livro.getNumPags());
            statement.setDouble(4, livro.getPreco());

            statement.execute();
            statement.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        } finally {
            Persistencia.closeConnection();
        }
    }
}
