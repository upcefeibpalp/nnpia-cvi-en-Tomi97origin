import { useState } from 'react';
import UserTable from "./components/UserTable";
import UserProfile from "./components/User"; // Importujte UserProfile
import { Typography, Container } from '@mui/material';

interface User {
    id: number;
    email: string;
    active: boolean;

}

function App() {
    const generateUsers = (count: number): User[] => {
        const users: User[] = [];
        for (let i = 0; i < count; i++) {
            const email = `user${i}@example.com`;
            const active = Math.random() < 0.5;
            users.push({ id: i + 1, email, active });
        }
        return users;
    };

    const [users, setUsers] = useState<User[]>(generateUsers(5));
    const [selectedUser, setSelectedUser] = useState<User | null>(null); // Stav pro vybraného uživatele pro profil
    const [isProfileOpen, setIsProfileOpen] = useState<boolean>(false); // Stav pro zobrazení profilu

    const handleToggleActive = (id: number) => {
        setUsers(users.map(user =>
            user.id === id ? { ...user, active: !user.active } : user
        ));
    };

    const handleOpenProfile = (user: User) => {
        setSelectedUser(user); // Uložíme vybraného uživatele do stavu
        setIsProfileOpen(true); // Nastavíme stav pro zobrazení profilu
    };

    const handleBackToList = () => {
        setIsProfileOpen(false); // Nastavíme stav pro skrytí profilu a zobrazení seznamu
        setSelectedUser(null); // Resetujeme vybraného uživatele
    };


    return (
        <Container maxWidth="md">
            <Typography variant="h4" component="h1" gutterBottom align="center" color="primary">
                Users:
            </Typography>

            {!isProfileOpen ? ( // Podmíněné renderování: pokud profil NENÍ otevřen, zobraz tabulku
                <UserTable
                    users={users}
                    onToggleActive={handleToggleActive}
                    onOpenProfile={handleOpenProfile} // Předáme handleOpenProfile funkci do UserTable
                />
            ) : (         // jinak (pokud profil JE otevřen), zobraz UserProfile
                selectedUser && ( // Zkontrolujeme, zda je selectedUser definován
                    <UserProfile user={selectedUser} onBackToList={handleBackToList} /> // Předáme vybraného uživatele a funkci pro návrat
                )
            )}
        </Container>
    );
}

export default App;