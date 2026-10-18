// 우리동네 맛·볼거리 Windows 10/11 실행기: 바탕화면·시작 메뉴에 아이콘을 만들고 Edge 앱 창으로 연다.
using System;
using System.Diagnostics;
using System.IO;
using System.Reflection;
using System.Windows.Forms;
using Microsoft.Win32;

[assembly: AssemblyTitle("맛·볼거리")]
[assembly: AssemblyProduct("우리동네 맛·볼거리")]
[assembly: AssemblyVersion("1.0.0.0")]
[assembly: AssemblyFileVersion("1.0.0.0")]

static class Launcher {
    const string Url = "https://edwinedwin0716-sketch.github.io/matjip-finder/";
    const string Caption = "우리동네 맛·볼거리";
    static string Dir { get { return Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData), "MatjipFinder"); } }

    [STAThread]
    static int Main() {
        try {
            string exe = Path.Combine(Dir, "맛볼거리.exe"), self = Assembly.GetExecutingAssembly().Location;
            if (!string.Equals(Path.GetFullPath(self), Path.GetFullPath(exe), StringComparison.OrdinalIgnoreCase)) {
                Directory.CreateDirectory(Dir);
                try { File.Copy(self, exe, true); } catch (IOException) { }
                Shortcut(Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.DesktopDirectory), "맛·볼거리.lnk"), exe);
                Shortcut(Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.Programs), "맛·볼거리.lnk"), exe);
            }
            Open();
            return 0;
        } catch (Exception e) {
            MessageBox.Show("실행하지 못했어요.\n\n" + e.Message, Caption, MessageBoxButtons.OK, MessageBoxIcon.Warning);
            return 1;
        }
    }

    // WScript.Shell 로 바로가기 만들기 (dynamic 없이 reflection)
    static void Shortcut(string lnk, string target) {
        try {
            var t = Type.GetTypeFromProgID("WScript.Shell"); object sh = Activator.CreateInstance(t);
            object sc = t.InvokeMember("CreateShortcut", BindingFlags.InvokeMethod, null, sh, new object[] { lnk });
            var st = sc.GetType();
            st.InvokeMember("TargetPath", BindingFlags.SetProperty, null, sc, new object[] { target });
            st.InvokeMember("WorkingDirectory", BindingFlags.SetProperty, null, sc, new object[] { Path.GetDirectoryName(target) });
            st.InvokeMember("IconLocation", BindingFlags.SetProperty, null, sc, new object[] { target + ",0" });
            st.InvokeMember("Description", BindingFlags.SetProperty, null, sc, new object[] { "주변 음식점·볼거리 추천" });
            st.InvokeMember("Save", BindingFlags.InvokeMethod, null, sc, null);
        } catch { }
    }

    static string Edge() {
        foreach (var hive in new[] { Registry.CurrentUser, Registry.LocalMachine }) {
            try { using (var k = hive.OpenSubKey(@"Software\Microsoft\Windows\CurrentVersion\App Paths\msedge.exe")) { var p = k == null ? null : k.GetValue("") as string; if (!string.IsNullOrEmpty(p) && File.Exists(p)) return p; } } catch { }
        }
        foreach (var root in new[] { Environment.GetEnvironmentVariable("ProgramFiles(x86)"), Environment.GetEnvironmentVariable("ProgramFiles") }) {
            if (string.IsNullOrEmpty(root)) continue;
            string p = Path.Combine(root, @"Microsoft\Edge\Application\msedge.exe"); if (File.Exists(p)) return p;
        }
        return null;
    }

    static void Open() {
        string edge = Edge();
        if (edge != null) Process.Start(new ProcessStartInfo(edge, "--app=" + Url + " --window-size=1180,860") { UseShellExecute = false });
        else Process.Start(new ProcessStartInfo(Url) { UseShellExecute = true });
    }
}
