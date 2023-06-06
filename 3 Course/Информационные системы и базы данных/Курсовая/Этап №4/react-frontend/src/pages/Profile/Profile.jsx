import * as React from "react";
import { Paper } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import TextField from "@mui/material/TextField";
import { Stack } from "@mui/system";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import dayjs from "dayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DesktopDatePicker } from "@mui/x-date-pickers/DesktopDatePicker";
import { useParams } from "react-router-dom";
import { preparationsList } from "../../helpers/preparations";
import { baaList } from "../../helpers/baa";
import DisplayCard from "../../components/card";

export default function Profile() {
  const [birthDate, setBirthDate] = React.useState(dayjs("2022-01-12"));
  const [sex, setSex] = React.useState("");
  const [dataToDisplay, setDataToDisplay] = React.useState([]);

  let h = window.innerHeight;
  let gridHeight = h - 60;

  const handleButtonPreparationClick = (event) => {
    // const url = "http://localhost:32456/sportsman/" + id + "/preparation";
    // axios({
    //   method: "get",
    //   url: url,
    // })
    //   .then(function (response) {
    //     let temp = response.data.body;
    //     setPreparations(temp);
    //   })
    //   .catch(function (error) {
    //     navigate("/sportsman/" + id);
    //   });
    setDataToDisplay(preparationsList);
  };

  const handleButtonBaaClick = (event) => {
    // const url = "http://localhost:32456/sportsman/" + id + "/baa";
    // axios({
    //   method: "get",
    //   url: url,
    // })
    //   .then(function (response) {
    //     let temp = response.data.body;
    //     setPreparations(temp);
    //   })
    //   .catch(function (error) {
    //     navigate("/sportsman/" + id);
    //   });
    setDataToDisplay(baaList);
  };

  return (
    <Box sx={{ height: gridHeight }}>
      <Grid
        container
        direction="row"
        alignItems="center"
        justifyContent="center"
        sx={{ height: "100%", p: "10px" }}
      >
        <Grid xs={12} md={6} sx={{ height: "100%", p: "10px" }}>
          <Paper
            elevation={3}
            sx={{
              height: "100%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Stack
              direction="column"
              sx={{
                height: "100%",
                width: "100%",
                p: "20px",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Grid
                container
                direction="row"
                alignItems="center"
                justifyContent="center"
                sx={{ height: "100%", width: "100%", p: "20px" }}
              >
                <Grid
                  xs={6}
                  sx={{
                    height: "100%",
                    p: "20px",
                    textAlign: "center",
                    display: "flex",
                    flexWrap: "wrap",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <Button
                    variant="contained"
                    sx={{ mb: 5 }}
                    onClick={handleButtonBaaClick}
                  >
                    Получить мои BAA
                  </Button>
                </Grid>
                <Grid
                  xs={6}
                  sx={{
                    height: "100%",
                    p: "20px",
                    textAlign: "center",
                    display: "flex",
                    flexWrap: "wrap",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <Button
                    variant="contained"
                    sx={{ mb: 5 }}
                    onClick={handleButtonPreparationClick}
                  >
                    Получить мои подготовки
                  </Button>
                </Grid>
              </Grid>
            </Stack>
          </Paper>
        </Grid>
        <Grid xs={12} md={6} sx={{ height: "100%", p: "10px" }}>
          <Paper elevation={3} sx={{ height: "100%" }}>
            <Box
              sx={{
                height: "100%",
                display: "flex",
                flexWrap: "wrap",
                justifyContent: "space-between",
                backgroundColor: "rgba(0,0,0,0)",
                overflow: "auto",
              }}
            >
              {dataToDisplay.map((element) => (
                <DisplayCard
                  key={element.id}
                  name={element.id}
                  title={element.name}
                  shortDescription={element.description}
                ></DisplayCard>
              ))}
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
