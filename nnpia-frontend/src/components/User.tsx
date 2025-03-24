import React from 'react';
import {
    Card,
    CardContent,
    Typography,
    Button,
    Stack,
} from '@mui/material';

interface UserProfileProps {
    user: { // Propsem předáme objekt uživatele
        id: number;
        email: string;
        active: boolean;
    };
    onBackToList: () => void; // Funkce pro návrat na seznam uživatelů
}

const User: React.FC<UserProfileProps> = ({ user, onBackToList }) => {
    return (
        <Card sx={{ maxWidth: 400, margin: 'auto', mt: 4 }}>
            <CardContent>
                <Typography variant="h5" component="div" gutterBottom>
                    Profil uživatele
                </Typography>
                <Stack spacing={2}>
                    <Typography variant="body1">
                        <strong>ID:</strong> {user.id}
                    </Typography>
                    <Typography variant="body1">
                        <strong>Email:</strong> {user.email}
                    </Typography>
                    <Typography variant="body1">
                        <strong>Stav:</strong> {user.active ? 'Aktivní' : 'Neaktivní'}
                    </Typography>
                </Stack>
            </CardContent>
            <CardContent sx={{ display: 'flex', justifyContent: 'space-between' }}>
                <Button variant="outlined" onClick={onBackToList}>
                    Zpět na seznam
                </Button>
                <Button variant="contained" color="primary" onClick={() => alert('Funkce úprav profilu by zde byla')}>
                    Upravit profil
                </Button>
            </CardContent>
        </Card>
    );
};

export default User;