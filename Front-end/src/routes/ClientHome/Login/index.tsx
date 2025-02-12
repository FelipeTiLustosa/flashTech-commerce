import './styles.css';
import { useContext, useState } from "react";
import * as authService from '../../../services/auth-service';
import * as forms from '../../../utils/forms';
import { useNavigate, useLocation } from "react-router-dom";
import { ContextToken } from "../../../utils/context-token";

export default function Login() {
    const { setContextTokenPayload } = useContext(ContextToken);
    const navigate = useNavigate();
    const location = useLocation();
    const [isActive, setIsActive] = useState(false);

    const [formData, setFormData] = useState<any>({
        username: {
            value: "",
            id: "username",
            name: "username",
            type: "email",
            placeholder: "Email",
            validation: function (value: string) {
                return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(value.toLowerCase());
            },
            message: "Favor informar um email válido",
        },
        password: {
            value: "",
            id: "password",
            name: "password",
            type: "password",
            placeholder: "Senha",
        }
    });

    const [formDataCadastro, setFormDataCadastro] = useState<any>({
        name: {
            value: "",
            id: "name",
            name: "name",
            type: "text",
            placeholder: "Nome",
        },
        username: {
            value: "",
            id: "username-cadastro",
            name: "username",
            type: "email",
            placeholder: "Email",
            validation: function (value: string) {
                return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(value.toLowerCase());
            },
            message: "Favor informar um email válido",
        },
        password: {
            value: "",
            id: "password-cadastro",
            name: "password",
            type: "password",
            placeholder: "Senha",
        }
    });

    function handleSubmit(event: any) {
        event.preventDefault();
        authService.loginRequest(forms.toValues(formData))
            .then(response => {
                authService.saveAccessToken(response.data.access_token);
                setContextTokenPayload(authService.getAccessTokenPayload());
                // Se o usuário veio do carrinho, volta para o carrinho
                const from = location.state?.from?.pathname || "/cart";
                navigate(from);
            }).catch(error => {
                console.log("Erro no login", error);
            });
    }

    function handleInputChange(event: any) {
        const name = event.target.name;
        const value = event.target.value;
        const result = forms.updateAndValidate(formData, name, value);
        setFormData(result);
    }

    function handleInputChangeCadastro(event: any) {
        const name = event.target.name;
        const value = event.target.value;
        const result = forms.updateAndValidate(formDataCadastro, name, value);
        setFormDataCadastro(result);
    }

    function handleCadastroSubmit(event: any) {
        event.preventDefault();
        // Implementar lógica de cadastro aqui
        console.log("Dados do cadastro:", forms.toValues(formDataCadastro));
    }

    return (
        <main>
            <section id="login-section">
                <div className={`container ${isActive ? 'active' : ''}`}>
                    <div className="form-container sign-up">
                        <form onSubmit={handleCadastroSubmit}>
                            <h1>Criar Conta</h1>
                            <div className="social-icons">
                                <a href="#" className="icon"><i className="fa-brands fa-google-plus-g"></i></a>
                                <a href="#" className="icon"><i className="fa-brands fa-facebook-f"></i></a>
                                <a href="#" className="icon"><i className="fa-brands fa-github"></i></a>
                                <a href="#" className="icon"><i className="fa-brands fa-linkedin-in"></i></a>
                            </div>
                            <span>ou use seu email para cadastro</span>
                            <input
                                type={formDataCadastro.name.type}
                                placeholder={formDataCadastro.name.placeholder}
                                name={formDataCadastro.name.name}
                                value={formDataCadastro.name.value}
                                onChange={handleInputChangeCadastro}
                            />
                            <input
                                type={formDataCadastro.username.type}
                                placeholder={formDataCadastro.username.placeholder}
                                name={formDataCadastro.username.name}
                                value={formDataCadastro.username.value}
                                onChange={handleInputChangeCadastro}
                            />
                            <input
                                type={formDataCadastro.password.type}
                                placeholder={formDataCadastro.password.placeholder}
                                name={formDataCadastro.password.name}
                                value={formDataCadastro.password.value}
                                onChange={handleInputChangeCadastro}
                            />
                            <button>Cadastrar</button>
                        </form>
                    </div>

                    <div className="form-container sign-in">
                        <form onSubmit={handleSubmit}>
                            <h1>Entrar</h1>
                            <div className="social-icons">
                                <a href="#" className="icon"><i className="fa-brands fa-google-plus-g"></i></a>
                                <a href="#" className="icon"><i className="fa-brands fa-facebook-f"></i></a>
                                <a href="#" className="icon"><i className="fa-brands fa-github"></i></a>
                                <a href="#" className="icon"><i className="fa-brands fa-linkedin-in"></i></a>
                            </div>
                            <span>ou use seu email e senha</span>
                            <input
                                type={formData.username.type}
                                placeholder={formData.username.placeholder}
                                name={formData.username.name}
                                value={formData.username.value}
                                onChange={handleInputChange}
                            />
                            <input
                                type={formData.password.type}
                                placeholder={formData.password.placeholder}
                                name={formData.password.name}
                                value={formData.password.value}
                                onChange={handleInputChange}
                            />
                            <a href="#">Esqueceu sua senha?</a>
                            <button>Entrar</button>
                        </form>
                    </div>

                    <div className="toggle-container">
                        <div className="toggle">
                            <div className="toggle-panel toggle-left">
                                <h1>Bem-vindo de volta!</h1>
                                <p>Entre com seus dados pessoais para utilizar todas as funcionalidades do site</p>
                                <button className="hidden" onClick={() => setIsActive(false)}>Entrar</button>
                            </div>
                            <div className="toggle-panel toggle-right">
                                <h1>Olá, Amigo!</h1>
                                <p>Cadastre-se com seus dados pessoais para utilizar todas as funcionalidades do site</p>
                                <button className="hidden" onClick={() => setIsActive(true)}>Cadastrar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    );
}