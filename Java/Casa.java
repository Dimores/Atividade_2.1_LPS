// View
class FrCadCasa (){
    ...

   private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (idCasaEditando > 0) {
                casaController.atualizarCasa(idCasaEditando, edtEndereco.getText(), edtArea.getText(), edtNumQuartos.getText(), edtPreco.getText());
            } else {
                casaController.cadastrarCasa(edtEndereco.getText(), edtArea.getText(), edtNumQuartos.getText(), edtPreco.getText());
            }
            //Comando bastante importante
            this.idCasaEditando = -1;

            casaController.atualizarTabela(grdCasas);

            this.habilitarCampos(false);
            this.limparCampos();
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
        } catch (CasaException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}

// Controller
CasaDao repositorio;
public saveCasa(String endereco, float area, int numQuartos, Double preco){
    ValidateCasa valid = new ValidateCasa();
    Casao casaOk = valid.validarCasa(endereco, area, numQuartos, preco);

		if(casaOk == null)
				throw new CasaException("Casa com pobrema");
		else
				repositorio.save(casaOk);

}

// Model
public class CasaDAO implements IDao {

    ...
    @Override
    public void save(Object obj) {
        Casa casa = (Casa) obj;

        sql = " INSERT INTO "
                + " casa(endereco, area, numQuartos, preco) "
                + " VALUES(?,?,?,?,?,?) ";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);

            //preencher cada ? com o campo adequado
            statement.setString(1, casa.getEndereco());
            statement.setFloat(2, casa.getArea());
            statement.setInt(3, casa.getNumQuartos());
            statement.setDouble(4, casa.getPreco());

            statement.execute();
            statement.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        } finally {
            Persistencia.closeConnection();
        }
    }

}




