function round(x)
    x = x + 0.5
    return math.floor(x)
end

sinVal = math.sin(counter*16*stepSize)*width+width

return "|" .. string.rep(" ", round(sinVal)) .. "|" .. string.rep(" ", round(width*2-sinVal)) .. "|"