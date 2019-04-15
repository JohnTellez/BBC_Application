<?php

require_once("conexion.php");
	
	class SignupUser {
		
		private $db;
        private $conexion;
		
		function __construct() {
			$this -> db = new Conectar();
			$this -> conexion = $this->db->conexion();
        }
        
		public function does_user_exist($email,$password,$url_image,$nombres)
		{
            $query = "Select * from usuario where email='$email'";
            $result = $this -> conexion->prepare($query);
            $result->execute();
            
			if($result->rowCount() == 1){
              
				$json['error'] = 'Ya existe un usuario con '.$email;
				echo json_encode($json);
			}else{
				//registro
                $hash = $this->hashSSHA($password);
                $encrypted_password = $hash["encrypted"]; // encrypted password
                $salt = $hash["salt"]; // salt
                
                $query = "insert into usuario (email, password, foto, nombres, salt) values (?,?,?,?,?)";
				$inserted = $this->conexion->prepare($query);
				
				$inserted->bindParam(1, $email, PDO::PARAM_STR); 
				$inserted->bindParam(2, $encrypted_password, PDO::PARAM_STR);
				$inserted->bindParam(3, $url_image, PDO::PARAM_STR);
				$inserted->bindParam(4, $nombres, PDO::PARAM_STR);
                $inserted->bindParam(5, $salt, PDO::PARAM_STR);
			
				if($inserted->execute()){
					$json['success'] = 'Cuenta creada';

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
						// Aquí puedes clasificar el error dependiendo de la excepción
						// para presentarlo en la respuesta Json
						return -1;
					}

				}else{
					$json['error'] = 'Se produjo un error';
				}
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
	
	$signupUser = new SignupUser();
	if(isset($_POST['email'],$_POST['password'],$_POST['nombres'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		$nombres = $_POST['nombres'];
		$image = $_POST['foto'];
		
		if($image!="no imagen"){
			$path  = "perfil_fotos/$nombres.jpg"; 
			$url_image = "perfil_fotos/".$nombres.".jpg";// para no presentar errores, agregarle el id al nombre de la imagen
			file_put_contents($path,base64_decode($image));
			//$bytesArchivo=file_get_contents($path);//para guardar la imagen en la tabla de la bbdd
		}else{
			$url_image = "sin imagen";
		}
        
		if(!empty($email) && !empty($password) && !empty($nombres)){			
			$encrypted_password = md5($password);
			$signupUser-> does_user_exist($email,$password,$url_image,$nombres);
			
		}else{
			echo json_encode("debe escribir ambas entradas");
		}
		
	}
?>