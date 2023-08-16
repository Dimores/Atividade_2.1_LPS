// View
class FrCadAnimal () {
    ...

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (idAnimalEditando > 0) {
                animalController.atualizarAnimal(idAnimalEditando, edtEspecie.getText(), edtRaca.getText(), edtIdade.getText(), edtPeso.getText());
            } else {
                animalController.cadastrarAnimal(edtEspecie.getText(), edtRaca.getText(), edtIdade.getText(), edtPeso.getText());
            }
            // Comando bastante importante
            this.idAnimalEditando = -1;

            animalController.atualizarTabela(grdAnimais);

            this.habilitarCampos(false);
            this.limparCampos();
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
        } catch (AnimalException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}

// Controller
AnimalDao repositorio;
public saveAnimal(String especie, String raca, float idade, Double peso) {
    ValidateAnimal valid = new ValidateAnimal();
    Animal animalOk = valid.validarAnimal(especie, raca, idade, peso);

    if (animalOk == null)
        throw new AnimalException("Animal com problema");
    else
        repositorio.save(animalOk);
}

// Model
public class AnimalDAO implements IDao {
    ...
    @Override
    public void save(Object obj) {
        Animal animal = (Animal) obj;

        sql = " INSERT INTO "
                + " animal(especie, raca, idade, peso) "
                + " VALUES(?,?,?,?,?,?) ";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);

            // Preencher cada ? com o campo adequado
            statement.setString(1, animal.getEspecie());
            statement.setString(2, animal.getRaca());
            statement.setDouble(3, animal.getIdade());
            statement.setDouble(4, animal.getPeso());

            statement.execute();
            statement.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        } finally {
            Persistencia.closeConnection();
        }
    }
}
