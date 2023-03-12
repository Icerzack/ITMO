import * as React from "react";
import { Paper } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import TextField from "@mui/material/TextField";
import { Stack } from "@mui/system";
import Button from "@mui/material/Button";
import Logo from "../../components/logo";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [login, setLogin] = React.useState("");
  const [password, setPassword] = React.useState("");

  const navigate = useNavigate();

  const handleLoginInput = (event) => {
    setLogin(event.currentTarget.value);
  };

  const handlePasswordInput = (event) => {
    setPassword(event.currentTarget.value);
  };

  const handleRegisterButtonClick = (event) => {
    navigate("/register");
  };

  const handleButtonClick = (event) => {
    const url =
      "http://localhost:32456/login?username=" +
      login +
      "&password=" +
      password;
    axios({
      method: "get",
      url: url,
    }).then(function (response) {
        localStorage.setItem("id", response.data.body[0].id);
        localStorage.setItem("login", login);
        localStorage.setItem("password", password);
        navigate("/sportsman/"+response.data.body[0].id);
    });
  };

  return (
    <Grid
      container
      spacing={0}
      direction="column"
      alignItems="center"
      justifyContent="center"
      textAlign="center"
      style={{ minHeight: "100vh" }}
    >
      <Grid item xs={3}>
        <Paper elevation={4}>
          <Stack
            sx={{
              p: 5,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
            direction="column"
            spacing={2}
          >
            <Logo />
            <TextField
              label="Login"
              variant="outlined"
              onChange={handleLoginInput}
            />
            <TextField
              type="password"
              label="Password"
              variant="outlined"
              onChange={handlePasswordInput}
            />
            <Button variant="contained" onClick={handleButtonClick}>
              Войти!
            </Button>
            <Button variant="text" onClick={handleRegisterButtonClick}>
              Зарегистрироваться
            </Button>
          </Stack>
        </Paper>
      </Grid>
    </Grid>
  );
}
