package application;

import application.dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControllerLogin {

	@FXML
	private Button btnEntrar;

	@FXML
	private Button btnSair;

	@FXML
	private PasswordField edtSenha;

	@FXML
	private TextField edtUsuario;

	@FXML
	private CheckBox mSenha;

	@FXML
	private TextField senhaVisivel;

	public void close() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Confirmação");
		alerta.setHeaderText("Deseja realmente sair?");
		alerta.setContentText("Clique em Sim para fechar o sistema.");

		ButtonType botaoSim = new ButtonType("Sim");
		ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

		alerta.getButtonTypes().setAll(botaoSim, botaoNao);

		ButtonType resultado = alerta.showAndWait().orElse(botaoNao);

		if (resultado == botaoSim) {
			System.exit(0);
		}
	}

	public void login() {
		String usuario = edtUsuario.getText();
		String senha = mSenha.isSelected() ? senhaVisivel.getText() : edtSenha.getText();
		loginComCredenciais(usuario, senha);
	}

	public void pe() {
		loginComCredenciais("1", "1");
	}

	private void loginComCredenciais(String usuario, String senha) {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			if (usuarioDAO.autenticar(usuario, senha)) {
				btnEntrar.getScene().getWindow().hide();
				Parent root = FXMLLoader.load(getClass().getResource("main.FXML"));
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setScene(scene);
				stage.setTitle("Sistema - ");
				stage.show();
				stage.setMaximized(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void mS() {
		boolean mostrar = mSenha.isSelected();

		senhaVisivel.setVisible(mostrar);
		senhaVisivel.setManaged(mostrar);

		edtSenha.setVisible(!mostrar);
		edtSenha.setManaged(!mostrar);

		if (mostrar) {
			senhaVisivel.setText(edtSenha.getText());
		} else {
			edtSenha.setText(senhaVisivel.getText());
		}
	}
}
