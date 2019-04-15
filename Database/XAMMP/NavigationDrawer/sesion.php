<?php

require_once("conexion.php");
	
	class LoginUser {
		
		private $db;
		private $conexion;
		
		function __construct() {
			$this -> db = new Conectar();
			$this -> conexion = $this->db->conexion();
		}
		
		public function does_user_exist($email,$password)
		{   
			$query = "Select * from usuario where email=?";
			$result = $this -> conexion->prepare($query);
			$result->execute(array($email));
            $result = $result->fetch(PDO::FETCH_ASSOC);
            
            $salt = $result['salt'];
            $encrypted_password = $result['password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
				$json['success'] = ' Bienvenido '.$email;
				// Al igual como en register_movil, con este json enviamos los datos al MainActivity
				$query = "SELECT id,email,foto,nombres FROM usuario WHERE email = ?";
				
				try {
					// Preparar sentencia
					$comando = $this->conexion->prepare($query);
					// Ejecutar sentencia preparada
					$comando->execute(array($email));
					// Capturar primera fila del resultado
					$row = $comando->fetch(PDO::FETCH_ASSOC);
					$json['usuario'][]=$row;
									
				} catch (PDOException $e) {
					$json['error'] = 'exception';
					// Aquí puedes clasificar el error dependiendo de la excepción
					// para presentarlo en la respuesta Json
					return -1;
				}

				echo json_encode($json);
            }else{
				$json['error'] = 'Las credenciales de inicio de sesión son incorrectas';
				echo json_encode($json);
			}
			
		}
		
        
        /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }
	}
	
	$loginUser = new LoginUser();
	if(isset($_POST['email'],$_POST['password'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		if(!empty($email) && !empty($password)){
			
			$encrypted_password = md5($password);
			$loginUser-> does_user_exist($email,$password);
			
		}else{
			echo json_encode("debe escribir ambas entradas");
		}
		
	}
?>