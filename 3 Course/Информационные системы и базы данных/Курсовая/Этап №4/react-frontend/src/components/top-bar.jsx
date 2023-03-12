import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import MenuIcon from "@mui/icons-material/Menu";
import Container from "@mui/material/Container";
import MenuItem from "@mui/material/MenuItem";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

import Logo from "./logo";

const pages = ["Спортивные добавки", "Упражнения", "Подготовки", "Профиль"];

function ResponsiveAppBar() {
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [currentPage, setCurrentPage] = React.useState("");

  const navigate = useNavigate();

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleMenuClick = (event) => {
    switch (event.currentTarget.getAttribute("name")) {
      case "Спортивные добавки":
        navigate("/sportsman/baa");
        setAnchorElNav(null);
        break;
      case "Тренировки":
        navigate("/sportsman/trainings");
        setAnchorElNav(null);
        break;
      case "Подготовки":
        navigate("/sportsman/preparations");
        setAnchorElNav(null);
        break;
      case "Профиль":
        const id = localStorage.getItem("id");
        const url = "/sportsman/" + id;
        navigate(url);
        setCurrentPage("Профиль");
        setAnchorElNav(null);
        break;
      default:
        break;
    }
  };

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Box
            sx={{ mr: "100px" }}
            // hidden={localStorage.getItem("id") === null ? "true" : "false"}
          >
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: "block" },
              }}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={handleMenuClick} name={page}>
                  <Typography textAlign="center">{page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <Logo></Logo>
          <Typography
            variant="h6"
            sx={{
              mr: 2,
              display: "flex",
              flexGrow: 1,
              justifyContent: "end",
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            {currentPage}
          </Typography>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;
