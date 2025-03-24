import React from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Button,
} from '@mui/material';

// Definice rozhraní pro uživatele (můžeme ji sem přesunout, nebo nechat v App.tsx a importovat)
interface User {
    id: number;
    email: string;
    active: boolean;
}

interface UserTableProps {
    users: User[];
    onToggleActive: (id: number) => void;
    onOpenProfile: (user: User) => void;
}

const UserTable: React.FC<UserTableProps> = ({ users, onToggleActive, onOpenProfile }) => {

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell align="left">Email</TableCell>
                        <TableCell align="left">Active</TableCell>
                        <TableCell align="left">Action</TableCell>
                        <TableCell align="left">Profile</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((user) => (
                        <TableRow
                            key={user.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {user.id}
                            </TableCell>
                            <TableCell align="left">{user.email}</TableCell>
                            <TableCell align="left">{user.active ? 'Active' : 'Inactive'}</TableCell>
                            <TableCell align="left">
                                <Button variant="contained" onClick={() => onToggleActive(user.id)}>
                                    {user.active ? 'Deactivate' : 'Activate'}
                                </Button>
                            </TableCell>
                            <TableCell align="left">
                                <Button variant="outlined" onClick={() => onOpenProfile(user)}> {/* Voláme onOpenProfile a předáváme uživatele */}
                                    Profil
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default UserTable;