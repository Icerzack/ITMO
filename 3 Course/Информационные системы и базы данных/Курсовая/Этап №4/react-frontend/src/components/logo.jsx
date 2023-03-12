import * as React from "react";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Star from "@mui/icons-material/Star";

export default function Logo() {
  return (
    <Stack
      direction="row"
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Typography
        variant="h6"
        sx={{
          display: "flex",
          fontFamily: "monospace",
          fontWeight: 700,
          letterSpacing: ".3rem",
          color: "inherit",
          textDecoration: "none",
        }}
      >
        MegaSport
      </Typography>
    </Stack>
  );
}
