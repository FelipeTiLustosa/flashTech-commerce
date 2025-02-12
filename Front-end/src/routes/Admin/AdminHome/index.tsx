import './styles.css';
import { useEffect, useState } from "react";
import { User } from "../../../types/user";
import * as userService from '../../../services/user-service';

export default function AdminHome() {
    const [user, setUser] = useState<User>();

    useEffect(() => {
        userService.findMe()
            .then(response => {
                console.log("Dados do usuário recebidos:", response.data);
                setUser(response.data);
            })
            .catch(error => {
                console.error("Erro ao carregar usuário:", error);
                if (error.response) {
                    console.error("Detalhes do erro:", {
                        status: error.response.status,
                        data: error.response.data
                    });
                }
            });
    }, []);

    return (
        <main>
            <section id="admin-home-section" className="dsc-container">
                <div className="dsc-welcome-admin">
                    <h2>
                        Bem-vindo à área administrativa {user?.name}
                    </h2>
                </div>
            </section>
        </main>
    );
}